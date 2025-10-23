package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public boolean create(Categorie o) {
        String sql = "INSERT INTO categorie (code, libelle) VALUES (?, ?)";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setString(1, o.getCode());
            ps.setString(2, o.getLibelle());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Categorie o) {
        String sql = "UPDATE categorie SET code = ?, libelle = ? WHERE id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setString(1, o.getCode());
            ps.setString(2, o.getLibelle());
            ps.setInt(3, o.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Categorie o) {
        String sql = "DELETE FROM categorie WHERE id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, o.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Categorie findById(int id) {
        String sql = "SELECT * FROM categorie WHERE id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categorie(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("libelle")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie";
        try (Statement st = HibernateUtil.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(new Categorie(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("libelle")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}

