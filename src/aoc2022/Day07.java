package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Day07 {
    static Directory root = new Directory("/");
    static Directory currentWorkingDirectory = root;

    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_07.txt"));
        System.out.println(problem2("resources/2022/test.txt"));
    }

    private static void generateFilesystem(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            String[] args = s.split(" ");

            if (args[0].equals("$")) {
                if (args[1].equals("cd")) {
                    if (args[2].equals("/")) {
                        currentWorkingDirectory = root;
                    } else if (args[2].equals("..")) {
                        currentWorkingDirectory = currentWorkingDirectory.getParent();
                    } else {
                        if (currentWorkingDirectory.containsDirectory(args[2])) {
                            currentWorkingDirectory = (Directory) currentWorkingDirectory.getFileByName(args[2]);
                        }
                    }
                } else if (args[1].equals("ls")) {
                    List<String> operations;
                    int index = i;

                    for (int j = i + 1; j < input.size(); j++) {
                        if (input.get(j).charAt(0) != '$') index = j;
                        else break;
                    }
                    operations = input.subList(i + 1, index + 1);

                    executeList(operations);
                }
            }
        }
    }

    private static int problem1(String filename) throws IOException {
        generateFilesystem(filename);
        root.printRecursively(0);

        return root.getRecursiveSumOfSize(100000);
    }

    private static int problem2(String filename) throws IOException {
        final int totalSpace = 70000000;
        final int updateSize = 30000000;
        int usedSpace = root.getSize();

        int neededSpace = updateSize - (totalSpace - usedSpace);

        return root.getBigDirectories(neededSpace).stream().min(Comparator.comparingInt(File::getSize)).get().getSize();
    }


    private static void executeList(List<String> input) {
        for (String s : input) {
            String[] args = s.split(" ");

            if (args[0].equals("dir")) {
                currentWorkingDirectory.addFileToDirectory(new Directory(args[1]));
            } else {
                currentWorkingDirectory.addFileToDirectory(new File(args[1], Integer.parseInt(args[0])));
            }
        }
    }

    public static void testing() {
        Directory directory = new Directory("/");
        directory.addFileToDirectory(new File("f", 1));
        directory.addFileToDirectory(new File("g", 2));
        Directory test = new Directory("a");
        test.addFileToDirectory(new File("test", 3));
        Directory test2 = new Directory("b");
        Directory test3 = new Directory("c");
        directory.addFileToDirectory(test);
        test.addFileToDirectory(test2);
        test2.addFileToDirectory(test3);
        test3.addFileToDirectory(new File("lastfile", 9));

        directory.printRecursively(0);

        System.out.println(directory.getRecursiveSumOfSize(100));
    }

    private static class Directory extends File {
        private List<File> files = new ArrayList<>();

        public Directory(String name) {
            super(name);
        }

        public int getSize() {
            return files.stream().mapToInt(File::getSize).sum();
        }

        public void addFileToDirectory(File file) {
            file.parent = this;
            this.files.add(file);
        }

        public boolean containsDirectory(String name) {
            for (File file : files) {
                if (file instanceof Directory) {
                    Directory temp = (Directory) file;
                    if (temp.getName().equals(name)) return true;
                }
            }
            return false;
        }

        public File getFileByName(String name) {
            for (File file : files) {
                if (file.getName().equals(name)) return file;
            }
            return null;
        }

        public boolean directoryOnlyContainsFiles() {
            for (File file : files) {
                if (file instanceof Directory) return false;
            }
            return true;
        }

        public int getRecursiveSumOfSize(int threshold) {
            if (directoryOnlyContainsFiles()) return this.getSize() < threshold ? this.getSize() : 0;

            int sum = this.getSize() < threshold ? this.getSize() : 0;

            for (File file : files) {
                if (file instanceof Directory) {
                    Directory temp = (Directory) file;
                    sum += temp.getRecursiveSumOfSize(threshold);
                }
            }
            return sum;
        }

        public void printRecursively(int depth) {
            System.out.printf("- %s (dir, totalsize=%d)%n", super.getName(), this.getSize());
            for (File file : files) {
                System.out.print("  ".repeat(depth + 1));
                if (!(file instanceof Directory)) {
                    System.out.println(file.toString());
                } else ((Directory) file).printRecursively(depth + 1);
            }
        }

        public List<Directory> getBigDirectories(int size) {
            List<Directory> possibleDirectories = new ArrayList<>();

            for (File file : this.files) {
                if (file instanceof Directory) {
                    Directory temp = (Directory) file;
                    if (temp.getSize() > size) possibleDirectories.add(temp);
                    if (!temp.directoryOnlyContainsFiles()) possibleDirectories.addAll(temp.getBigDirectories(size));
                }
            }
            return possibleDirectories;
        }
    }

    private static class File {
        private String name;
        private int size;

        private Directory parent = null;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public File(String name) {
            this(name, 0);
        }

        public int getSize() {
            return this.size;
        }

        public String getName() {
            return this.name;
        }

        public Directory getParent() {
            return this.parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            File file = (File) o;
            return size == file.size && Objects.equals(name, file.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, size);
        }

        @Override
        public String toString() {
            return String.format("- %s (file, size=%d)", this.name, this.size);
        }
    }
}
