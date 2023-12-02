package lab1;

import java.time.LocalDate;
import java.util.Objects;

public class FileEntry {
    private String name;
    private long size;
//    private final LocalDate created;
    //    private LocalDate updated;
    private Mode mode;

    public FileEntry(String name, long size) {
        this.name = name;
        this.size = size;
//        this.created = LocalDate.now();
//        this.updated = this.created;
        this.mode = Mode.Write;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        FileEntry fe = (FileEntry) o;
        return this.name.equals(fe.name) &&
                this.size == fe.size &&
//                this.created.equals(fe.created) &&
//                this.updated.equals(fe.updated) &&
                this.mode.equals(fe.mode);
    }

//    public LocalDate getCreated() {
//        return this.created;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, mode);
    }

    @Override
    public String toString() {
        return "FileEntry{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
//                "', created='" + created +
//                "', updated='" + updated +
                ", mod='" + mode + '\'' +
                "}";
    }
}
