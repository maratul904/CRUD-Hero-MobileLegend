package controller;

import config.KoneksiDatabase;
import model.Hero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeroController {

    public void insertHero(Hero hero) {
        String sql = "INSERT INTO tm_hero (nama_hero, kategori, gender) VALUES (?, ?, ?)";
        try (Connection conn = KoneksiDatabase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hero.getNama());
            stmt.setString(2, hero.getKategori());
            stmt.setString(3, hero.getGender());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Hero> getAllHeroes() {
        List<Hero> list = new ArrayList<>();
        String sql = "SELECT * FROM tm_hero";
        try (Connection conn = KoneksiDatabase.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Hero(
                        rs.getInt("id_hero"),
                        rs.getString("nama_hero"),
                        rs.getString("kategori"),
                        rs.getString("gender")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateHero(Hero hero) {
        String sql = "UPDATE tm_hero SET nama_hero=?, kategori=?, gender=? WHERE id_hero=?";
        try (Connection conn = KoneksiDatabase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hero.getNama());
            stmt.setString(2, hero.getKategori());
            stmt.setString(3, hero.getGender());
            stmt.setInt(4, hero.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHero(int id) {
        String sql = "DELETE FROM tm_hero WHERE id_hero=?";
        try (Connection conn = KoneksiDatabase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
