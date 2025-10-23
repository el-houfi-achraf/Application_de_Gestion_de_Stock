package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        String sql = "INSERT INTO produit (reference, prix) VALUES (?, ?)";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setString(1, o.getReference());
            ps.setFloat(2, o.getPrix());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit o) {
        String sql = "UPDATE produit SET reference = ?, prix = ? WHERE id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setString(1, o.getReference());
            ps.setFloat(2, o.getPrix());
            ps.setInt(3, o.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit o) {
        String sql = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, o.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit findById(int id) {
        String sql = "SELECT * FROM produit WHERE id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Produit(
                    rs.getInt("id"),
                    rs.getString("reference"),
                    rs.getFloat("prix")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Produit> findAll() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";
        try (Statement st = HibernateUtil.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                produits.add(new Produit(
                    rs.getInt("id"),
                    rs.getString("reference"),
                    rs.getFloat("prix")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    // Méthode pour afficher les produits par catégorie
    public List<Produit> findByCategorie(int categorieId) {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT p.* FROM produit p " +
                     "INNER JOIN categorie_produit cp ON p.id = cp.produit_id " +
                     "WHERE cp.categorie_id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, categorieId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                produits.add(new Produit(
                    rs.getInt("id"),
                    rs.getString("reference"),
                    rs.getFloat("prix")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    // Méthode pour afficher les produits commandés dans une commande
    public List<Object[]> findProduitsInCommande(int commandeId) {
        List<Object[]> resultats = new ArrayList<>();
        String sql = "SELECT p.reference, p.prix, lcp.quantite " +
                     "FROM produit p " +
                     "INNER JOIN lignecommandeproduit lcp ON p.id = lcp.produit_id " +
                     "WHERE lcp.commande_id = ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] ligne = new Object[3];
                ligne[0] = rs.getString("reference");
                ligne[1] = rs.getFloat("prix");
                ligne[2] = rs.getInt("quantite");
                resultats.add(ligne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }

    // Méthode pour afficher les produits dont le prix est supérieur à 100 DH
    public List<Produit> findProduitsExpensifs(float prixMinimum) {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE prix > ?";
        try (PreparedStatement ps = HibernateUtil.getConnection().prepareStatement(sql)) {
            ps.setFloat(1, prixMinimum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                produits.add(new Produit(
                    rs.getInt("id"),
                    rs.getString("reference"),
                    rs.getFloat("prix")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }
}

