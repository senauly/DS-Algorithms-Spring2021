public abstract class Person{
    private String name;
    private String surname;
    private String emailAdress;
    private String password;
    
    protected Person(String name, String surname, String email){
        setName(name);
        setSurname(surname);
        setEmail(email);
    }

    protected Person(String name, String surname){
        setName(name);
        setSurname(surname);
    }

    protected Boolean login(String email, String password){
        return true;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setEmail(String email){
        this.emailAdress = email;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getEmail(){
        return emailAdress;
    }

    @Override
    public String toString() { 
        return("Name: " + getName() + "\n" + "Surname: " + getSurname()); 
    } 
}
