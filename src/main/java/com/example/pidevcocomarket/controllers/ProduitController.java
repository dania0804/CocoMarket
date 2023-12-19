package com.example.pidevcocomarket.controllers;

import com.example.pidevcocomarket.repositories.BoutiqueRepository;
import com.example.pidevcocomarket.repositories.ProduitRepository;
import com.example.pidevcocomarket.repositories.StockRepository;
import com.example.pidevcocomarket.entities.*;
import com.example.pidevcocomarket.interfaces.IProduitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/Produit")
@AllArgsConstructor
@Validated
public class ProduitController {

    IProduitService produitService;
    ProduitRepository produitRepository;
    StockRepository stockRepository;
    BoutiqueRepository boutiqueRepository;
@Autowired
private ObjectMapper objectMapper;

    @PostMapping(value= "/ajouterProduit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> ajouterProduit(@RequestParam("file") MultipartFile file,@RequestParam String pp) throws JsonProcessingException {
        Produit p = objectMapper.readValue(pp, Produit.class);
        try {
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            produitService.ajouterProduit(p);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PutMapping("/modifierProduit")
    Produit modifierProduit(@RequestBody Produit p) {
        return produitService.modifierProduit(p);
    }

    @GetMapping("/afficherProduits")
    List<Produit> afficherProduit() {
        return produitService.afficherListeProduit();
    }

    @DeleteMapping("/supprimerProduit/{id}")
    void supprimerProduit(@PathVariable int id) {
        produitService.deleteProduit(id);
    }

    @GetMapping("afficherProduit/{id}")
    Produit retriveProduit(@PathVariable int id) {
        return produitService.retrieveProduit(id);
    }

    /*  @PutMapping("/affectProduitToBoutique/{idProduit}/{idBoutique}")
      void affecterProduitBoutique(Integer idProduit, Integer idBoutique) {
          produitService.ProduitAffectBoutique(idProduit, idBoutique);
      }

      @PutMapping("/affectProduitToBoutiqueAndStock/{idProduit}/{idBoutique}/{idStock}")
      void affecterProduitBoutiqueAndStock(Integer idProduit, Integer idBoutique,Integer idStock) {
          produitService.ProduitAffectBoutiqueAndStock(idProduit,idBoutique,idStock);
      }
  */
    @GetMapping("/add-producttt")
    public String ShowAddProductForm(Model model) {
        List<String> basicColors = produitService.getBasicColors();
        model.addAttribute("basicColors", basicColors);
        model.addAttribute("produit", new Produit());
        return "produit";

    }


    @GetMapping("/search")
    public List<Produit> searchProducts(@RequestParam("q") String query) {
        return produitService.findProductsByName(query);
    }

    //récupérer les produits pertinents après une recherche et recommander des produits
// similaires basés sur les descriptions associés à ces produits.
    @GetMapping("/searchByDescription")
    public List<Produit> rechercheProducts(@RequestParam("q") String query) {
        List<Produit> relevantProducts = produitService.findProductsByDescription(query);
        List<Produit> recommendedProducts = new ArrayList<>();
        for (Produit product : relevantProducts) {
            List<Produit> similarProducts = findSimilarProducts(product);
            recommendedProducts.addAll(similarProducts);
        }
        return recommendedProducts;
    }

    private List<Produit> findSimilarProducts(Produit product) {
        List<Produit> allProducts = produitService.afficherListeProduit();
        List<Produit> similarProducts = new ArrayList<>();

        for (Produit p : allProducts) {
            if (p != product && p.getDescription().equals(product.getDescription())) {
                similarProducts.add(p);
            }
        }
        return similarProducts;
    }

    @PostMapping ("/produit/{idProduit}/affect/testerPromo")
    public ResponseEntity<String> affectProduitToBoutiqueCategorieStock(@PathVariable Integer idProduit, @RequestParam Integer idBoutique, @RequestParam Integer idCategorie, @RequestParam Integer idStock) {
        produitService.ProduitAffectBoutiqueCategorieStock(idProduit, idBoutique, idCategorie, idStock);
        return ResponseEntity.ok("Produit " + idProduit + " a été affecté à la boutique " + idBoutique + ", la catégorie " + idCategorie + " et le stock " + idStock + ".");
    }


}
