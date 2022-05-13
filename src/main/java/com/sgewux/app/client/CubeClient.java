package com.sgewux.app.client;

import java.util.List;

import com.sgewux.app.models.Cube;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CubeClient {
    @RequestLine("GET /cubes")
    List<Cube> getAllCubes();
    
    @RequestLine("GET /cubes/{serialNumber}")
    Cube getCubeBySn(@Param("serialNumber") String serialNumber);

    @RequestLine("POST /cubes/add")
    @Headers("Content-Type: application/json")
    Cube addNewCube(String newCube);
}
