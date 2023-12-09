package lab4;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class FileEntry {
    private String name;
    private long size;
    private LocalDate created;
    private Mode mode;

    public FileEntry(String name, long size) {
        this.name = name;
        this.size = size;
        this.created = LocalDate.now();
        this.mode = Mode.Write;
    }

    public static class Builder {
        @NotBlank(message = "File name cannot be blank")
        private String name;

        @Positive(message = "File size must be a positive value")
        private long size;

        public Builder name(String fileName) {
            this.name = fileName;
            return this;
        }

        public Builder size(long fileSize) {
            this.size = fileSize;
            return this;
        }

        public FileEntry build() {
            validate();

            FileEntry fe = new FileEntry(this.name, this.size);
            return fe;
        }

        private void validate() {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<Builder>> violations = validator.validate(this);

            if (!violations.isEmpty()) {
                List<String> errorMessages = new ArrayList<>();
                for (ConstraintViolation<Builder> violation : violations) {
                    errorMessages.add(violation.getMessage());
                }
                throw new IllegalArgumentException(String.join(", ", errorMessages));
            }
        }
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }


    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getCreated() {
        return this.created;
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
                this.created.equals(fe.created) &&
                this.mode.equals(fe.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, mode);
    }

    @Override
    public String toString() {
        return "FileEntry{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", created='" + created + '\'' +
                ", mod='" + mode + '\'' +
                "}";
    }
}
