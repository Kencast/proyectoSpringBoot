package com.example.demo.material;

import com.example.demo.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping("/get")
    public List<Material> getAllMaterial() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Material>> getMaterialById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(materialService.getMaterialsByChannelId(id));
        }
        catch(Exception e){
            System.out.println("Error in get material by channel id: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPerson(@RequestBody Material material) {
        try{
            System.out.println(material.getChannel());
            materialService.insertMaterial(material);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating material."+e.getMessage());
        }
    }

}
