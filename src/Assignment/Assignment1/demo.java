//package Assignment.Assignment1;
//
//public class demo {
//    if (commands[0].equals("echo")) {
//        // 确保命令数组长度足够
//        if (commands.length < 4) {
//            System.out.println("Invalid command parameters for echo.");
//            return;
//        }
//        // 分离文件内容和路径
//        String content = commands[1];
//        String[] path = commands[3].split("/");
//
//        // 如果路径长度小于 1，直接返回
//        if (path.length == 0) {
//            System.out.println("Invalid path.");
//            return;
//        }
//
//        // 最后一个是文件名，前面的是目录路径
//        String fileName = path[path.length - 1];
//
//        Node pointer = current; // 从当前目录开始
//        // 遍历目录路径（不包含最后一个文件名）
//        for (int j = 0; j < path.length - 1; j++) {
//            String part = path[j];
//            if (part.equals("") || part.equals(".")) {
//                continue;
//            } else if (part.equals("..")) {
//                if (pointer.parent != null) {
//                    pointer = pointer.parent;
//                }
//                continue;
//            }
//            boolean found = false;
//            for (Node child : pointer.children) {
//                if (child.name.equals(part) && child.type == Type.folder) {
//                    pointer = child; // 进入子目录
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                // 如果目录不存在，则可以选择报错或者创建目录，根据需求来决定
//                System.out.println("Directory " + part + " not found.");
//                return;
//            }
//        }
//        // 创建文件节点，并添加到当前目录中
//        Node node = new Node(i, fileName, Type.file, content, pointer);
//        pointer.addChild(node);
//    }
//
//}
