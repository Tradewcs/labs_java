package lab4;


import jakarta.validation.constraints.Positive;

public class Main {
    @Positive
    int num;

    public Main(int num) {
        this.num = num;
    }

    public static void main(String[] args) {
        try {
            FileEntry fileEntry = new FileEntry.Builder()
                    .name("")
                    .size(-1024)
                    .build();

            System.out.println(fileEntry);
        } catch (IllegalArgumentException e) {
//            System.err.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

}
