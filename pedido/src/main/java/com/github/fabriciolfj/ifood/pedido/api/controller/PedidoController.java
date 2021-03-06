package com.github.fabriciolfj.ifood.pedido.api.controller;

import com.github.fabriciolfj.ifood.pedido.domain.Localizacao;
import com.github.fabriciolfj.ifood.pedido.domain.Pedido;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

    private static final Logger LOG = Logger.getLogger(PedidoController.class);

    @Inject
    Vertx vertx;

    @Inject
    EventBus eventBus;

    void startup(@Observes Router router) {
        router.route("/localizacoes*").handler(localizacaoHandler());
    }

    private SockJSHandler localizacaoHandler() {
        SockJSHandler handler = SockJSHandler.create(vertx);
        PermittedOptions permitted = new PermittedOptions();
        permitted.setAddress("novaLocalizacao");

        //Alterado na versoa 1.9
        //        BridgeOptions bridgeOptions = new BridgeOptions().addOutboundPermitted(permitted);
        //        handler.bridge(bridgeOptions);

        SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addOutboundPermitted(permitted);
        handler.bridge(bridgeOptions);
        return handler;
    }

    @GET
    public List<PanacheMongoEntityBase> findAll() {
        LOG.info("Listando todos os pedidos");
        return Pedido.listAll();
    }

    @POST
    @Path("{idPedido}/localizacao")
    public Pedido novaLocalizacao(@PathParam("idPedido") String idPedido, Localizacao localizacao) {
        Pedido pedido = Pedido.findById(new ObjectId(idPedido));
        pedido.setLocalizacaoEntregador(localizacao);

        String json = JsonbBuilder.create().toJson(localizacao);
        eventBus.sendAndForget("novaLocalizacao", json);
        pedido.persistOrUpdate();
        return pedido;
    }
}

