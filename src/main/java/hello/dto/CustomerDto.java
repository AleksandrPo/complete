package hello.dto;

public class CustomerDto {
    //TODO: Fill with data corresponding to Customer entity

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;

    public CustomerDto(){}
    public CustomerDto(String firstName, String lastName, String email, int age){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private int age;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setAge(int age) {
            this.age = age;
            return this;
        }
        public CustomerDto build() {
            return new CustomerDto(firstName, lastName, email, age);
        }
    }
}
