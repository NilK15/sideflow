package dev.nil.sideflow.auth.domain.command;

import dev.nil.sideflow.auth.dto.LoginRequestResource;
import lombok.Builder;

// Not really needed, but isolates and create pure unit testing (loginrequest resource has validations, etc)
@Builder
public record LoginCommand(LoginRequestResource loginRequestResource) {
}