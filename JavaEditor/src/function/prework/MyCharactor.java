package function.prework;
public class MyCharactor {
    public String name;
    public int occurence;
    public String firstName, lastName;
    public String searchName;
    public int[] density = new int[20];
    boolean flag;
    public MyCharactor(String name) {
        this.name = name;
        this.occurence = 0;
        this.firstName = name.substring(0, name.length() / 2);
        this.lastName = name.substring(name.length() / 2, name.length());
        if (firstName.compareTo("笹垣") == 0 || firstName.compareTo("古贺") == 0 || firstName.compareTo("今枝") == 0) {
            this.flag = true;
            searchName = firstName;
        }
        else {
            searchName = lastName;
            this.flag = false;
        }
    }
    public void count(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (i + name.length() < text.length() && text.substring(i, i + name.length()).compareTo(name) == 0) {
                occurence++;
                density[i * 20 / text.length()]++;
            }
            else if (i + lastName.length() < text.length() && text.substring(i, i + lastName.length()).compareTo(lastName) == 0) {
                occurence++;
                density[i * 20 / text.length()]++;
            }
            else if (flag && i + firstName.length() < text.length() && text.substring(i, i + firstName.length()).compareTo(firstName) == 0) {
                occurence++;
                density[i * 20 / text.length()]++;
            }
        }
    }
}
