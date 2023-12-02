package lab1;

public class Explorer {
    private DirectoryEntry currentDirectory;

    public Explorer(DirectoryEntry de) {
        this.currentDirectory = de;
    }

    /***
     *
     * @param name name of directory to be entered
     */
    public void enterDirectory(String name) {
        currentDirectory = currentDirectory.getSubDirectory(name);
    }

    /***
     * move to the parent directory
     */
    public void traversal() {
        traversal_p(currentDirectory);
    }

    private void traversal_p(DirectoryEntry root) {
        if (root != null) {
            System.out.print(root.getName() + " ");

            for (DirectoryEntry de : root.getDirectories()) {
                traversal_p(de);
            }
        }
    }

}
