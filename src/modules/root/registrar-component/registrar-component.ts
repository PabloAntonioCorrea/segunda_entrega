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
  selector: "app-registrar-component",
  imports: [
    MatFormField,
    MatInput,
    MatButton,
    MatLabel,
    MatError,
    ReactiveFormsModule,
    RouterLink,
  ],
  templateUrl: "./registrar-component.html",
  styleUrl: "./registrar-component.css",
})
export class RegistrarComponent {
  form: FormGroup;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(3)]],
    });
  }

  protected onSubmit() {
    if (this.form.valid) {
      const { username, password } = this.form.value;

      this.authService.register(username, password).subscribe({
        next: (response) => {
          console.log("Registro com sucesso, token", response.token);
          this.authService.setToken(response.token);
          this.router.navigate(["/home"]);
        },
        error: (err) => {
          console.log("Registro falhou", err);
        },
      });
    }
  }
}
