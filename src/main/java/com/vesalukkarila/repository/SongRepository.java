package com.vesalukkarila.repository;

import com.vesalukkarila.model.Song;
import com.vesalukkarila.web.exception.SongNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Repository for managing song entities in the database.
 * This class provides methods to perform CRUD operations on songs,
 * including retrieving all songs, finding a song by ID, saving a new song,
 * updating an existing song, and deleting a song from the database.
 */
@Repository
public class SongRepository implements ISongRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final SongRowMapper songRowMapper = new SongRowMapper();

    /**
     * Constructs a new SongRepository with the provided JDBCTemplate.
     *
     * @param jdbcTemplate the JdbcTemplate used for database operations
     */
    public SongRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all songs from the database.
     *
     * @return a list of all song entities
     */
    @Override
    public List<Song> findAll() {
        String sql = "SELECT id, name, artist, publishYear FROM songs";
        return jdbcTemplate.query(sql, songRowMapper);
    }

    /**
     * Finds a song by its unique identifier.
     *
     * @param id, the unique identifier of the song
     * @return the song entity associated with the given ID
     * @throws SongNotFoundException if the song with the specified ID does not exist
     */
    @Override
    public Song findById(String id) {
        String sql = "SELECT id, name, artist, publishYear FROM songs WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(sql, songRowMapper, id);
        } catch (Exception e) {
            throw new SongNotFoundException(id);
        }
    }

    /**
     * Saves a new song to the database.
     *
     * @param song, the song entity to be saved
     */
    @Override
    public void save(Song song) {
        String sql = "INSERT INTO songs (id, name, artist, publishYear) VALUES (?,?,?,?)";
        Object[] args = new Object[]{song.getId().toString(), song.getName(),
                song.getArtist(), song.getPublishYear()};
        jdbcTemplate.update(sql, args);
    }

    /**
     * Updates an existing song in the database.
     *
     * @param song, the song entity containing updated information
     */
    @Override
    public void update(Song song) {
        String sql = "UPDATE songs SET name = ?, artist = ?, publishYear = ? WHERE id=?";
        Object[] args = new Object[]{song.getName(), song.getArtist(),
                song.getPublishYear(), song.getId().toString()};
        jdbcTemplate.update(sql, args);
    }

    /**
     * Deletes a song from the database by its unique identifier.
     *
     * @param id, the unique identifier of the song to be deleted
     * @return true if the song was successfully deleted, false otherwise
     */
    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM songs WHERE id=?";
        Object[] args = new Object[]{id};
        return jdbcTemplate.update(sql, args) == 1;
    }

    /**
     * Checks if a song already exists in the database based on its name, artist, and publish year.
     *
     * @param song, the song} entity to check for existence
     * @return true if the song exists, false otherwise
     */
    public boolean songExists(Song song) {
        String sql = "SELECT COUNT(*) FROM songs WHERE name = ? AND artist = ? AND publishYear = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                song.getName(), song.getArtist(), song.getPublishYear());
        return count != null && count > 0;
    }

    /**
     * RowMapper implementation for mapping SQL ResultSets to song objects.
     */
    private static class SongRowMapper implements RowMapper<Song> {
        @Override
        public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
            Song song = new Song();
            song.setId(UUID.fromString(rs.getString("id")));
            song.setName(rs.getString("name"));
            song.setArtist(rs.getString("artist"));
            song.setPublishYear(rs.getInt("publishYear"));
            return song;
        }
    }
}
