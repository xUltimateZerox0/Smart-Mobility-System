package com.smartmobility.service;

public interface FlottaService {

    boolean analisiStatoFlotta(Long idFlotta);

    boolean bloccaMezzo(Long idMezzo);

    boolean avviaManutenzione(Long idFlotta);
}
