package com.developwithdaniele.sp07.mapper;

import com.developwithdaniele.sp07.model.Actor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorRowMapper implements RowMapper<Actor> {
    @Override
    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actor actor = new Actor();
        actor.setActorId(rs.getLong("actor_id"));
        actor.setFirstName(rs.getString("first_name"));
        actor.setLastName(rs.getString("last_name"));
        Date lastUpdate = rs.getDate("last_update");
        actor.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
        return actor;
    }
}
