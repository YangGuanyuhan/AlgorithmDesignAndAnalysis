package Assignment.Assignment1;

import java.util.ArrayList;
import java.util.Scanner;

enum TypeA1 {
    file, folder
}

public class A1 {
    static NodeA1 root;
    static NodeA1 current;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        in.nextLine();

        // 初始化初始目录，名称为"."
        root = new NodeA1(0, ".", TypeA1.folder, null, null);
        current = root;

        // 处理 create/update 命令
        for (int i = 0; i < n; i++) {
            String command = in.nextLine();
            if (command.startsWith("mkdir ")) {
                // mkdir 命令
                String pathStr = command.substring(6).trim();
                mkdirCommand(pathStr, i);
            } else if (command.startsWith("echo")) {
                // echo 命令
                echoCommand(command, i);
            } else if (command.startsWith("rm")) {
                // rm 或 rm -rf 命令
                rmCommand(command);
            } else if (command.startsWith("mv ")) {
                // mv 命令
                mvCommand(command);
            }
        }

        // 处理查询命令
        for (int i = 0; i < m; i++) {
            String commandLine = in.nextLine();
            if (commandLine.startsWith("cat ")) {
                catCommand(commandLine);
            } else if (commandLine.startsWith("find")) {
                findCommand(commandLine);
            }
        }
    }

    // 辅助方法：从给定起始节点出发，根据路径获取目标节点（允许文件时 allowFile 为 true）
    public static NodeA1 getNodeByPath(String path, NodeA1 start) {
        String[] parts = path.split("/");
        NodeA1 node = start;
        for (String part : parts) {
            if (part.equals("") || part.equals(".")) continue;
            if (part.equals("..")) {
                if (node.parent != null) {
                    node = node.parent;
                } else {
                    return null;
                }
            } else {
                boolean found = false;
                for (NodeA1 child : node.children) {
                    if (child.name.equals(part)) {
                        node = child;
                        found = true;
                        break;
                    }
                }
                if (!found) return null;
            }
        }
        return node;
    }

    // mkdir 命令：根据路径依次创建目录（支持 “.” 和 “..”）
    public static void mkdirCommand(String pathStr, int index) {
        String[] parts = pathStr.split("/");
        NodeA1 node = current; // 从当前目录开始
        for (String part : parts) {
            if (part.equals("") || part.equals(".")) continue;
            if (part.equals("..")) {
                if (node.parent != null) {
                    node = node.parent;
                }
                continue;
            }
            // 检查子目录中是否已存在该目录
            NodeA1 next = null;
            for (NodeA1 child : node.children) {
                if (child.name.equals(part) && child.type == TypeA1.folder) {
                    next = child;
                    break;
                }
            }
            if (next == null) {
                next = new NodeA1(index, part, TypeA1.folder, null, node);
                node.addChild(next);
            }
            node = next;
        }
    }

    // echo 命令：创建或更新文件
    public static void echoCommand(String commandLine, int index) {
        int arrowIndex = commandLine.indexOf(">");
        if (arrowIndex == -1) {
            System.out.println("Invalid echo command: missing '>'");
            return;
        }
        // 提取内容（可能为空）
        String content = commandLine.substring(5, arrowIndex).trim();
        String filePathStr = commandLine.substring(arrowIndex + 1).trim();
        // 分离出父目录和文件名
        int lastSlash = filePathStr.lastIndexOf("/");
        NodeA1 parentDir;
        String fileName;
        if (lastSlash == -1) {
            parentDir = current;
            fileName = filePathStr;
        } else {
            String dirPath = filePathStr.substring(0, lastSlash);
            fileName = filePathStr.substring(lastSlash + 1);
            parentDir = getNodeByPath(dirPath, current);
            if (parentDir == null) {
                System.out.println("No such directory: " + dirPath);
                return;
            }
        }
        // 检查文件是否存在，若存在则更新内容
        boolean updated = false;
        for (NodeA1 child : parentDir.children) {
            if (child.name.equals(fileName) && child.type == TypeA1.file) {
                child.content = content;
                updated = true;
                break;
            }
        }
        if (!updated) {
            NodeA1 newFile = new NodeA1(index, fileName, TypeA1.file, content, parentDir);
            parentDir.addChild(newFile);
        }
    }

    // rm 命令：删除文件或文件夹（对于文件夹支持 -rf 递归删除）
    public static void rmCommand(String commandLine) {
        String[] parts = commandLine.split(" ");
        String pathStr;
        if (parts.length == 2) {
            pathStr = parts[1];
        } else {
            // 格式为 rm -rf [path]
            pathStr = parts[2];
        }
        NodeA1 target = getNodeByPath(pathStr, current);
        if (target == null) {
            System.out.println("No such file or directory.");
            return;
        }
        if (target.parent != null) {
            target.parent.children.remove(target);
        }
    }

    // mv 命令：将文件或目录从 srcPath 移动到目标文件夹 dstPath
    public static void mvCommand(String commandLine) {
        String[] parts = commandLine.split(" ");
        if (parts.length < 3) return;
        String srcPath = parts[1];
        String dstPath = parts[2];
        NodeA1 srcNode = getNodeByPath(srcPath, current);
        NodeA1 dstNode = getNodeByPath(dstPath, current);
        if (srcNode == null || dstNode == null || dstNode.type != TypeA1.folder) {
            System.out.println("No such file or directory.");
            return;
        }
        if (srcNode.parent != null) {
            srcNode.parent.children.remove(srcNode);
        }
        dstNode.addChild(srcNode);
        srcNode.parent = dstNode;
    }

    // cat 命令：输出文件内容
    public static void catCommand(String commandLine) {
        String filePath = commandLine.substring(4).trim();
        NodeA1 fileNode = getNodeByPath(filePath, current);
        if (fileNode == null || fileNode.type != TypeA1.file) {
            System.out.println("No such file or directory.");
            return;
        }
        System.out.println(fileNode.content == null ? "" : fileNode.content);
    }

    // find 命令：从指定路径开始查找所有满足条件的文件或目录
    public static void findCommand(String commandLine) {
        String[] parts = commandLine.split(" ");
        String pathArg = ".";
        String typeFilter = null;
        String nameFilter = null;
        // 判断第二个参数是否为起始路径（若不是选项则视为路径）
        int index = 1;
        if (parts.length > 1 && !parts[1].startsWith("-")) {
            pathArg = parts[1];
            index = 2;
        }
        // 解析选项参数
        while (index < parts.length) {
            if (parts[index].equals("-type") && index + 1 < parts.length) {
                typeFilter = parts[index + 1];
                index += 2;
            } else if (parts[index].equals("-name") && index + 1 < parts.length) {
                nameFilter = parts[index + 1];
                index += 2;
            } else {
                index++;
            }
        }
        NodeA1 startNode = getNodeByPath(pathArg, current);
        if (startNode == null) {
            System.out.println("No such file or directory.");
            return;
        }
        ArrayList<NodeA1> resultList = new ArrayList<>();
        findHelper(startNode, typeFilter, nameFilter, resultList);
        System.out.println(resultList.size());
        for (NodeA1 node : resultList) {
            System.out.println(getFullPath(node));
        }
    }

    // 递归查找符合条件的节点
    public static void findHelper(NodeA1 node, String typeFilter, String nameFilter, ArrayList<NodeA1> resultList) {
        boolean match = true;
        if (typeFilter != null) {
            if (typeFilter.equals("f") && node.type != TypeA1.file) {
                match = false;
            } else if (typeFilter.equals("d") && node.type != TypeA1.folder) {
                match = false;
            }
        }
        if (nameFilter != null && !node.name.equals(nameFilter)) {
            match = false;
        }
        if (match) {
            resultList.add(node);
        }
        if (node.type == TypeA1.folder) {
            for (NodeA1 child : node.children) {
                findHelper(child, typeFilter, nameFilter, resultList);
            }
        }
    }

    // 获取节点的完整路径（从根目录开始，根节点名称为"."）
    public static String getFullPath(NodeA1 node) {
        if (node.parent == null) {
            return node.name;
        } else {
            String parentPath = getFullPath(node.parent);
            if (parentPath.equals(".")) {
                return "." + "/" + node.name;
            } else {
                return parentPath + "/" + node.name;
            }
        }
    }
}

class NodeA1 {
    int index;
    String name;
    TypeA1 type;
    String content;
    NodeA1 parent;
    ArrayList<NodeA1> children;

    public NodeA1(int index, String name, TypeA1 type, String content, NodeA1 parent) {
        this.index = index;
        this.name = name;
        this.type = type;
        this.content = content;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public void addChild(NodeA1 child) {
        children.add(child);
    }
}
