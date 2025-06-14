package org.udesa.unoback.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.service.UnoService;


import java.util.List;
import java.util.UUID;


// controller nos dice mapeo esto a una pagina web. Quiero escribir/devolver una pagina web
// 2 tipos de controler
// - REST Controller: para hacer aplicaciones REST full. Es mas simpke
// - Controller: este gestiona HTML



@Controller
public class UnoController {
    //@GetMapping("/") // le mapeo a la raiz de la pagina web este mensaje, en el localhost 8080
    //public String saludo() { return "index"; }
    @Autowired UnoService unoService;


    @GetMapping("/hola")
    public ResponseEntity<String> holaMundo() {
        return new ResponseEntity<>("Respuesta a hola mundo", HttpStatus.OK);
    }

    //@PostMapping ("newmatch") public ResponseEntity newMatch(@RequestParam List<String> players) {
    //    return ResponseEntity.ok(UUID.randomUUID());
    //}

    @PostMapping ("newmatch") public ResponseEntity newMatch(@RequestParam List<String> players) {
        return ResponseEntity.ok(unoService.newMatch(players));
    }
    @PostMapping("play/{matchId}/{player}") public ResponseEntity play( @PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        unoService.play(matchId, player, card);
        return ResponseEntity.ok().build(); // CONSULTAR SI ESTO ESTA BIEN
    }
    @PostMapping("draw/{matchId}/{player}") public ResponseEntity drawCard( @PathVariable UUID matchId, @PathVariable String player ) {
        unoService.drawCard(matchId, player);
        return ResponseEntity.ok().build();
    }
    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard( @PathVariable UUID matchId ) {
        return ResponseEntity.ok(unoService.activeCard(matchId));
    }

    @GetMapping("playerhand/{matchId}")
    public ResponseEntity playerHand(@PathVariable UUID matchId) {
        return ResponseEntity.ok( unoService.playerHand( matchId ).stream().map( each -> each.asJson()));
    }

    // Datos inválidos, parámetros mal formados, incompletos o incorrectos... podría ser un internal error o runtime, algo de bajo nivel
    // Jugada invalida, jugador inexistente, carta incorrecta, turno incorrecto... podría ser un ilegal argument, algo a nivel de negocio.

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegal(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body( exception.getMessage() );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException exception) {
        return ResponseEntity.internalServerError().body( exception.getMessage() );
    }
}
