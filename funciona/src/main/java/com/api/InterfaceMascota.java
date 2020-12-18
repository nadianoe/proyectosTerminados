package com.api;

import org.bson.Document;

public interface InterfaceMascota {

    String Saludar(Boolean esElDueño);

    AccesoMongoDB accesoAMongoDB = new AccesoMongoDB();
}
