package com.smartmobility.integration;

import org.springframework.stereotype.Service;

@Service
public class MezzoIoTServiceImpl implements MezzoIoTService {

    @Override
    public boolean bloccoMezzoFisico(Long idMezzo) {
        return true;
    }

    @Override
    public boolean sbloccoMezzoFisico(Long idMezzo) {
        return true;
    }
}
