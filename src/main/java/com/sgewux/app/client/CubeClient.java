package com.sgewux.app.client;

import java.util.List;

import com.sgewux.app.models.Cube;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CubeClient {
    @RequestLine("GET /cubes")
    List<Cube> getAllCubes();
    
    @RequestLine("POST /cubes/add")
    @Headers("Content-Type: application/json")
    Cube addNewCube(String newCube);
    
    @RequestLine("GET /cubes/{serialNumber}")
    Cube getCubeBySerial(@Param("serialNumber") String serialNumber);

    @RequestLine("PUT /cubes/{serialNumber}")
    Cube updateCube(@Param("serialNumber") String serialNumber, String newCube);

    @RequestLine("DELETE /cubes/{serialNumber}")
    void deleteCube(@Param("serialNumber") String serialNumber);

}
