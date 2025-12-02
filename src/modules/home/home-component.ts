import { Component } from "@angular/core";
import { RouterOutlet, RouterLink } from "@angular/router";
import { MatToolbar } from "@angular/material/toolbar";
import { MatIcon } from "@angular/material/icon";
import { MatTooltip } from "@angular/material/tooltip";
import { AuthService } from "../../core/services/auth-service";
import { Router } from "@angular/router";

@Component({
  selector: "app-home-component",
  imports: [RouterOutlet, RouterLink, MatToolbar, MatIcon, MatTooltip],
  templateUrl: "./home-component.html",
  styleUrl: "./home-component.css",
})
export class HomeComponent {
  constructor(private authService: AuthService, private router: Router) {}

  protected logout() {
    this.authService.logout();
  }

  protected getUser() {
    return this.authService.getUser();
  }
}
