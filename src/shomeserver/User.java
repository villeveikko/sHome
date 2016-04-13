
package shomeserver;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Käyttäjä-luokka. Käytetään lähinnä kirjautumisen yhteydessä.
 * @author Villeveikko
 */
public class User implements Serializable {
    private String username;
    private String password;
    private String view;
    
    public User() {
        this.username = "";
        this.password = "";
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(String username, String password, String view) {
        this.username = username;
        this.password = password;
        this.view = view;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    Iterator<String> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
