package br.com.marcelpinotti.jsonviewsexample.dtos.user;

import br.com.marcelpinotti.jsonviewsexample.dtos.views.UserViews;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    @JsonView(UserViews.RestrictedData.class)
    private Long id;
    @JsonView({UserViews.RestrictedData.class, UserViews.SaveData.class})
    private String name;
    @JsonView({UserViews.RestrictedData.class, UserViews.SaveData.class})
    private String lastname;
    @JsonView({UserViews.RestrictedData.class, UserViews.SaveData.class})
    private String email;
    @JsonView({UserViews.SaveData.class, UserViews.CompleteData.class})
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDto user = (UserDto) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
