package fi.academy;

public class Todo {
    private int id;
    private String todo;
    private boolean valmis;

    public Todo() {
    }

    public Todo(String todo, boolean valmis) {
        this.todo = todo;
        this.valmis = valmis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isValmis() {
        return valmis;
    }

    public void setValmis(boolean valmis) {
        this.valmis = valmis;
    }

    @Override
    public String toString() {
        return "Numero: " + id + "/n Tehtävä: " + todo;
    }
}
