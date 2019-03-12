package com.bester.platform.platformchain.controller.lastrip;

import com.google.gson.JsonElement;
import com.gxchain.client.GXChainClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuwen
 * @date 2019/3/8
 */
@RestController
public class CharactersController {

    private static final String PRIVATE_KEY = "5KTE1uL3fdtkuzsHye3y8ej3gxF1aGQY2Los6W8veNrfvgCCnUe";
    private static final String ACCOUNT_NAME = "matrixdapp1";
    private static final String NET_POINT = "https://node1.gxb.io";
    private static final String CONTRACT = "genesis";

    private static GXChainClient gxChainClient = new GXChainClient(PRIVATE_KEY, ACCOUNT_NAME, NET_POINT);

    @GetMapping("/genesis/characterCount")
    public String characterCount() {
        final String table = "characount";
        JsonElement characterCount = gxChainClient.getTableRows(CONTRACT, table, 0, 1);
        return characterCount.toString();
    }

    @GetMapping("/genesis/character")
    public String characters(int id) {
        final String table = "character";
        JsonElement characterCount = gxChainClient.getTableRows(CONTRACT, table, id, 1);
        return characterCount.toString();
    }
}
