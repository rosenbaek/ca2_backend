package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Size(max = 255)
    @Column(name = "user_pass")
    private String userPass;

    @JoinTable(name = "watchlist", joinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
        @JoinColumn(name  = "station_id", referencedColumnName = "station_id")})
    @ManyToMany
    private List<Station> stations = new ArrayList<>();

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;

    @JoinTable(name = "user_roles", joinColumns = {
      @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
      @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    public List<String> getRolesAsStrings() {
      if (roleList.isEmpty()) {
        return null;
      }
      List<String> rolesAsStrings = new ArrayList<>();
      roleList.forEach((role) -> {
          rolesAsStrings.add(role.getRoleName());
        });
      return rolesAsStrings;
    }

    public User() {}

    //pw is plain password
    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, userPass));
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public String getUserPass() {
      return this.userPass;
    }

    public void setUserPass(String userPass) {
      this.userPass = userPass;
    }

    public List<Role> getRoleList() {
      return roleList;
    }

    public void setRoleList(List<Role> roleList) {
      this.roleList = roleList;
    }

    public void addRole(Role userRole) {
      roleList.add(userRole);
    }




    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        this.stations.add(station);
        if (!station.getUserList().contains(this)) {
            station.addUser(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.userPass);
        hash = 89 * hash + Objects.hashCode(this.userName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.userPass, other.userPass)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        return true;
    }
    
    
    

}
