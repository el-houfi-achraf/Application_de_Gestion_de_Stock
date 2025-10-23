package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Initialisation des services
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        System.out.println("=== Test de l'application de gestion de stock ===\n");

        // 1. Créer des catégories
        System.out.println("1. Création des catégories...");
        Categorie cat1 = new Categorie("CAT1", "Ordinateurs");
        Categorie cat2 = new Categorie("CAT2", "Imprimantes");
        Categorie cat3 = new Categorie("CAT3", "Accessoires");

        categorieService.create(cat1);
        categorieService.create(cat2);
        categorieService.create(cat3);
        System.out.println("Catégories créées avec succès!\n");

        // 2. Créer des produits
        System.out.println("2. Création des produits...");
        Produit p1 = new Produit("E512", 120.0f);
        Produit p2 = new Produit("2B85", 100.0f);
        Produit p3 = new Produit("EE85", 200.0f);
        Produit p4 = new Produit("AA12", 50.0f);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);
        produitService.create(p4);
        System.out.println("Produits créés avec succès!\n");

        // 3. Créer des commandes
        System.out.println("3. Création des commandes...");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Commande cmd1 = new Commande(sdf.parse("14/03/2013"));
            Commande cmd2 = new Commande(new Date());

            commandeService.create(cmd1);
            commandeService.create(cmd2);
            System.out.println("Commandes créées avec succès!\n");

            // 4. Ajouter des lignes de commande
            System.out.println("4. Ajout de produits aux commandes...");
            // Pour la commande 1 (id = 4 selon l'exemple)
            ligneCommandeService.create(new LigneCommandeProduit(1, 1, 7));    // E512: 7 unités
            ligneCommandeService.create(new LigneCommandeProduit(1, 2, 14));   // 2B85: 14 unités
            ligneCommandeService.create(new LigneCommandeProduit(1, 3, 5));    // EE85: 5 unités

            // Pour la commande 2
            ligneCommandeService.create(new LigneCommandeProduit(2, 2, 10));
            ligneCommandeService.create(new LigneCommandeProduit(2, 4, 20));
            System.out.println("Lignes de commande ajoutées avec succès!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TEST 1: Afficher la liste des produits par catégorie
        System.out.println("\n=== TEST 1: Afficher les produits par catégorie ===");
        System.out.println("Produits de la catégorie 1:");
        List<Produit> produitsCat1 = produitService.findByCategorie(1);
        for (Produit p : produitsCat1) {
            System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH");
        }

        // TEST 2: Afficher la liste des produits commandés entre deux dates
        System.out.println("\n=== TEST 2: Afficher les produits commandés dans une commande ===");
        System.out.println("Commande : 4       Date : 14 Mars 2013");
        System.out.println("Liste des produits :");
        System.out.println("Référence    Prix    Quantité");

        List<Object[]> produitsCommande = produitService.findProduitsInCommande(1);
        for (Object[] ligne : produitsCommande) {
            String reference = (String) ligne[0];
            float prix = (float) ligne[1];
            int quantite = (int) ligne[2];
            System.out.printf("%-12s %.0f DH  %d%n", reference, prix, quantite);
        }

        // TEST 3: Afficher les produits commandés dans une commande donnée
        System.out.println("\n=== TEST 3: Détails d'une commande spécifique ===");
        Commande cmd = commandeService.findById(1);
        if (cmd != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            System.out.println("Commande : " + cmd.getId() + "       Date : " + sdf.format(cmd.getDate()));
            System.out.println("Liste des produits :");
            System.out.println("Référence    Prix    Quantité");

            List<Object[]> produits = produitService.findProduitsInCommande(cmd.getId());
            for (Object[] ligne : produits) {
                String reference = (String) ligne[0];
                float prix = (float) ligne[1];
                int quantite = (int) ligne[2];
                System.out.printf("%-12s %.0f DH  %d%n", reference, prix, quantite);
            }
        }

        // TEST 4: Afficher les produits dont le prix est supérieur à 100 DH
        System.out.println("\n=== TEST 4: Produits avec prix > 100 DH ===");
        List<Produit> produitsExpensifs = produitService.findProduitsExpensifs(100.0f);
        System.out.println("Produits trouvés : " + produitsExpensifs.size());
        for (Produit p : produitsExpensifs) {
            System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH");
        }

        // Afficher toutes les commandes
        System.out.println("\n=== Liste de toutes les commandes ===");
        List<Commande> commandes = commandeService.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Commande c : commandes) {
            System.out.println("Commande ID: " + c.getId() + " - Date: " + sdf.format(c.getDate()));
        }

        // Afficher tous les produits
        System.out.println("\n=== Liste de tous les produits ===");
        List<Produit> tousProduits = produitService.findAll();
        for (Produit p : tousProduits) {
            System.out.println(p);
        }

        System.out.println("\n=== Tests terminés avec succès! ===");
    }
}

