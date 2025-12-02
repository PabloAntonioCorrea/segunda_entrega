import { Component, OnInit } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { Prato } from "../../../core/models/prato";
import { PratoService } from "../../../core/services/prato-service";
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
  selector: "app-prato-component",
  imports: [
    MatIcon,
    MatButton,
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    MatLabel,
    MatError,
  ],
  templateUrl: "./prato-component.html",
  styleUrl: "./prato-component.css",
})
export class PratoComponent implements OnInit {
  editing = false;

  form: FormGroup = new FormGroup({});
  dados: Prato[] = [];

  constructor(
    private pratoService: PratoService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      id: [null],
      nome: [null, [Validators.required]],
      preco: [null, [Validators.required, Validators.min(0.01)]],
    });
  }

  ngOnInit(): void {
    this.pratoService.getPratos().subscribe({
      next: (pratos) => {
        this.dados = pratos;
      },
      error: (err) => {
        console.error("Failed to fetch pratos:", err);
      },
    });
  }

  protected adicionarPrato() {
    if (this.form.valid) {
      const { nome, preco } = this.form.value;
      this.pratoService.create({ nome, preco } as Prato).subscribe({
        next: (prato) => {
          this.dados = [...this.dados, prato];
          this.resetForm();
        },
        error: (err) => {
          console.error("Failed to create prato:", err);
        },
      });
    }
  }

  protected deletarPrato(prato: Prato) {
    if (confirm("Tem certeza que deseja excluir este prato?")) {
      this.pratoService.delete(prato.id as number).subscribe({
        next: () => {
          this.dados = this.dados.filter((p) => p.id !== prato.id);
          this.snackBar.open("Prato excluído com sucesso!", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["success-snackbar"],
          });
        },
        error: (err) => {
          console.error("Failed to delete prato:", err);
          this.snackBar.open("Erro ao excluir prato", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["error-snackbar"],
          });
        },
      });
    }
  }

  protected editarPrato(prato: Prato) {
    this.editing = true;

    this.form.setValue({
      id: prato.id,
      nome: prato.nome,
      preco: prato.preco,
    });
  }

  protected atualizarPrato() {
    if (this.form.valid) {
      const { id, nome, preco } = this.form.value;

      this.pratoService.update({ id, nome, preco } as Prato).subscribe({
        next: (pratoAtualizado) => {
          this.dados = this.dados.map((prato) =>
            prato.id === id ? pratoAtualizado : prato
          );
          this.resetForm();
          this.snackBar.open("Prato atualizado com sucesso!", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["success-snackbar"],
          });
        },
        error: (err) => {
          console.error("Failed to update prato:", err);
          const errorMessage =
            err.error?.message ||
            "Não é possível atualizar. Verifique se o nome já está cadastrado.";
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

  protected formatarPreco(preco: number): string {
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(preco);
  }

  private resetForm() {
    this.form.reset();
    this.editing = false;
  }
}
