package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public boolean create(LigneCommandeProduit o) {
        String sql = "INSERT INTO lignecommandeproduit (commande_id, produit_id, quantite) VALUES (?, ?, ?)";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, o.getIdCommande());
            ps.setInt(2, o.getIdProduit());
            ps.setInt(3, o.getQuantite());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(LigneCommandeProduit o) {
        String sql = "UPDATE lignecommandeproduit SET quantite = ? WHERE commande_id = ? AND produit_id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, o.getQuantite());
            ps.setInt(2, o.getIdCommande());
            ps.setInt(3, o.getIdProduit());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(LigneCommandeProduit o) {
        String sql = "DELETE FROM lignecommandeproduit WHERE commande_id = ? AND produit_id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, o.getIdCommande());
            ps.setInt(2, o.getIdProduit());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LigneCommandeProduit findById(int id) {
        // Cette méthode n'a pas beaucoup de sens pour une table de liaison
        return null;
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        List<LigneCommandeProduit> lignes = new ArrayList<>();
        String sql = "SELECT * FROM lignecommandeproduit";
        try (Statement st = HibernateUtil.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lignes.add(new LigneCommandeProduit(
                    rs.getInt("commande_id"),
                    rs.getInt("produit_id"),
                    rs.getInt("quantite")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lignes;
    }

    public List<LigneCommandeProduit> findByCommande(int commandeId) {
        List<LigneCommandeProduit> lignes = new ArrayList<>();
        String sql = "SELECT * FROM lignecommandeproduit WHERE commande_id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lignes.add(new LigneCommandeProduit(
                    rs.getInt("commande_id"),
                    rs.getInt("produit_id"),
                    rs.getInt("quantite")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lignes;
    }
}

