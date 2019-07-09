package fi.academy.dao.jdbc;

import fi.academy.Todo;
import fi.academy.dao.TodoDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Qualifier("jdbc")
public class TodoDaoJdbcImplem implements TodoDao {
    private Connection con;

    public TodoDaoJdbcImplem() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tehtavat", "postgres", "java19");
    }

    @Override
    public List<Todo> haeKaikki() {
        String sql = "SELECT * FROM todos";
        List<Todo> haetut = new ArrayList<>();
        try (PreparedStatement pr = con.prepareStatement(sql)) {
            for (ResultSet rs = pr.executeQuery(); rs.next(); ) {
                Todo t = new Todo();
                t.setId(rs.getInt("id"));
                t.setTodo(rs.getString("todo"));
                t.setValmis(rs.getBoolean("valmis"));
                haetut.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        return haetut;
    }

    @Override
    public int lisaa(Todo todo) {
        int avain = -1;
        String sql = "INSERT INTO todos(todo, valmis) VALUES (?,?)";
        try (PreparedStatement pr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, todo.getTodo());
            pr.setBoolean(2, todo.isValmis());
            pr.execute();
            ResultSet avaimet = pr.getGeneratedKeys();
            while (avaimet.next()) {
                avain = avaimet.getInt(1);
                todo.setId(avain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avain;
    }

    @Override
    public Todo poista(int id) {
        Todo poistettu = new Todo();
        String sel = "SELECT * FROM todos WHERE id = ?";
        try (PreparedStatement prs = con.prepareStatement(sel)) {
            prs.setInt(1, id);
            prs.execute();
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                poistettu.setId(rs.getInt("id"));
                poistettu.setTodo(rs.getString("todo"));
                poistettu.setValmis(rs.getBoolean("valmis"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "DELETE FROM todos WHERE id = ?";
        try (PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, id);
            pr.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return poistettu;
    }
}
