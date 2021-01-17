package com.hyoseok.dynamicdatasource.web.http;

import com.hyoseok.dynamicdatasource.config.security.JwtUtils;
import com.hyoseok.dynamicdatasource.web.http.request.CreateJwtRequest;
import com.hyoseok.dynamicdatasource.web.http.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {

    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<SuccessResponse> createJwt(@RequestBody @Valid CreateJwtRequest request) {
        String jwt = jwtUtils.generateToken(request.getAdminId(), request.getHasRole());
        return ResponseEntity.created(URI.create("/jwt")).body(new SuccessResponse(jwt));
    }
}
