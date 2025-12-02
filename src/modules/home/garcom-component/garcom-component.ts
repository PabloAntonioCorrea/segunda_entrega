import { Component, OnInit } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { Garcom } from "../../../core/models/garcom";
import { GarcomService } from "../../../core/services/garcom-service";
import { MatError, MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: "app-garcom-component",
  imports: [
    MatButton,
    MatIcon,
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    MatLabel,
    MatError,
  ],
  templateUrl: "./garcom-component.html",
  styleUrl: "./garcom-component.css",
})
export class GarcomComponent implements OnInit {
  editing = false;
  form: FormGroup = new FormGroup({});
  dados: Garcom[] = [];

  constructor(
    private garcomService: GarcomService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      id: [null],
      nome: [null, [Validators.required]],
      matricula: [
        null,
        [Validators.required, Validators.pattern(/^GAR\d{4}$/)],
      ],
    });
  }

  ngOnInit(): void {
    this.garcomService.getGarcons().subscribe({
      next: (garcons) => {
        this.dados = garcons;
      },
      error: (err) => {
        console.error("Failed to fetch garcons:", err);
      },
    });
  }

  protected adicionarGarcom() {
    if (this.form.valid) {
      const { nome, matricula } = this.form.value;
      this.garcomService.create({ nome, matricula } as Garcom).subscribe({
        next: (garcom) => {
          this.dados = [...this.dados, garcom];
          this.resetForm();
        },
        error: (err) => {
          console.error("Failed to create garcom:", err);
        },
      });
    }
  }

  protected editarGarcom(garcom: Garcom) {
    this.editing = true;
    this.form.setValue({
      id: garcom.id,
      nome: garcom.nome,
      matricula: garcom.matricula,
    });
  }

  protected atualizarGarcom() {
    if (this.form.valid) {
      const { id, nome, matricula } = this.form.value;
      this.garcomService.update({ id, nome, matricula } as Garcom).subscribe({
        next: (garcomAtualizado) => {
          this.dados = this.dados.map((garcom) =>
            garcom.id === id ? garcomAtualizado : garcom
          );
          this.resetForm();
          this.snackBar.open("Garçom atualizado com sucesso!", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["success-snackbar"],
          });
        },
        error: (err) => {
          console.error("Failed to update garcom:", err);
          const errorMessage =
            err.error?.message ||
            "Não é possível atualizar. Verifique se a matrícula já está cadastrada.";
          this.snackBar.open(errorMessage, "Fechar", {
            duration: 4000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["error-snackbar"],
          });
        },
      });
    }
  }

  protected deletarGarcom(garcom: Garcom) {
    if (confirm("Tem certeza que deseja excluir este garçom?")) {
      this.garcomService.delete(garcom.id as number).subscribe({
        next: () => {
          this.dados = this.dados.filter((g) => g.id !== garcom.id);
          this.snackBar.open("Garçom excluído com sucesso!", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["success-snackbar"],
          });
        },
        error: (err) => {
          console.error("Failed to delete garcom:", err);
          this.snackBar.open("Erro ao excluir garçom", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["error-snackbar"],
          });
        },
      });
    }
  }

  private resetForm() {
    this.form.reset();
    this.editing = false;
  }
}
