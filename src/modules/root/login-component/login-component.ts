import { Component } from "@angular/core";
import { MatError, MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatButton } from "@angular/material/button";
import { Router, RouterLink } from "@angular/router";
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { AuthService } from "../../../core/services/auth-service";

@Component({
  selector: "app-login-component",
  imports: [
    MatFormField,
    MatInput,
    MatButton,
    MatLabel,
    MatError,
    ReactiveFormsModule,
    RouterLink,
  ],
  templateUrl: "./login-component.html",
  styleUrl: "./login-component.css",
})
export class LoginComponent {
  form: FormGroup;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required]],
    });
  }

  protected onSubmit() {
    if (this.form.valid) {
      const { username, password } = this.form.value;

      this.authService.login(username, password).subscribe({
        next: (response) => {
          console.log("Login com sucesso, token", response.token);
          this.authService.setToken(response.token);
          this.router.navigate(["/home"]);
        },
        error: (err) => {
          console.log("Login falhou", err);
        },
      });
    }
  }
}
