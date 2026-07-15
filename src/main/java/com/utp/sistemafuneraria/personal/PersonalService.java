package com.utp.sistemafuneraria.personal;

import java.util.List;

import com.utp.sistemafuneraria.personal.PersonalDTO.CreateRequest;
import com.utp.sistemafuneraria.personal.PersonalDTO.ListItem;

public interface PersonalService {
    List<ListItem> listar();
    ListItem crear(CreateRequest request);
}
