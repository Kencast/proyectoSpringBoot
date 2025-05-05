package com.example.demo.material;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Material> getAllMaterials() {return materialRepository.findAll();}

    public List<Material> getMaterialsByChannelId(Long channelId) {
        return materialRepository.getMaterialsByChannelId(channelId);
    }

    public void insertMaterial(Material material) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("createMaterial");
        query.setParameter("titleM", material.getTitle());
        query.setParameter("descriptionM", material.getDescription());
        query.setParameter("channelID", material.getChannel());
        query.execute();
    }
}
