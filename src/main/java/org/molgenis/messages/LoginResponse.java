package org.molgenis.messages;

public class LoginResponse {

    private String token;
    private String username;
    private String firstname;
    private String lastname;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponse that = (LoginResponse) o;

        if (getToken() != null ? !getToken().equals(that.getToken()) : that.getToken() != null) return false;
        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        if (getFirstname() != null ? !getFirstname().equals(that.getFirstname()) : that.getFirstname() != null)
            return false;
        return getLastname() != null ? getLastname().equals(that.getLastname()) : that.getLastname() == null;

    }

    @Override
    public int hashCode() {
        int result = getToken() != null ? getToken().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getFirstname() != null ? getFirstname().hashCode() : 0);
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}