import { Component, OnInit } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { Cliente } from "../../../core/models/cliente";
import { ClienteService } from "../../../core/services/cliente-service";
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
  selector: "app-cliente-component",
  imports: [
    MatIcon,
    MatButton,
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    MatLabel,
    MatError,
  ],
  templateUrl: "./cliente-component.html",
  styleUrl: "./cliente-component.css",
})
export class ClienteComponent implements OnInit {
  editing = false;

  form: FormGroup = new FormGroup({});
  dados: Cliente[] = [];

  constructor(
    private clienteService: ClienteService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      id: [null],
      nome: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      telefone: [
        null,
        [Validators.required, Validators.pattern(/\(\d{2}\)\s\d{4,5}-\d{4}/)],
      ],
    });
  }

  ngOnInit(): void {
    this.clienteService.getClientes().subscribe({
      next: (clientes) => {
        this.dados = clientes;
      },
      error: (err) => {
        console.error("Failed to fetch clientes:", err);
      },
    });
  }

  protected adicionarCliente() {
    if (this.form.valid) {
      const { nome, email, telefone } = this.form.value;
      this.clienteService
        .create({ nome, email, telefone } as Cliente)
        .subscribe({
          next: (cliente) => {
            this.dados = [...this.dados, cliente];
            this.resetForm();
          },
          error: (err) => {
            console.error("Failed to create cliente:", err);
          },
        });
    }
  }

  protected deletarCliente(cliente: Cliente) {
    if (confirm("Tem certeza que deseja excluir este cliente?")) {
      this.clienteService.delete(cliente.id as number).subscribe({
        next: () => {
          this.dados = this.dados.filter((c) => c.id !== cliente.id);
          this.snackBar.open("Cliente excluído com sucesso!", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["success-snackbar"],
          });
        },
        error: (err) => {
          console.error("Failed to delete cliente:", err);
          this.snackBar.open("Erro ao excluir cliente", "Fechar", {
            duration: 3000,
            horizontalPosition: "center",
            verticalPosition: "top",
            panelClass: ["error-snackbar"],
          });
        },
      });
    }
  }

  protected editarCliente(cliente: Cliente) {
    this.editing = true;

    this.form.setValue({
      id: cliente.id,
      nome: cliente.nome,
      email: cliente.email,
      telefone: cliente.telefone,
    });
  }

  protected atualizarCliente() {
    if (this.form.valid) {
      const { id, nome, email, telefone } = this.form.value;

      this.clienteService
        .update({ id, nome, email, telefone } as Cliente)
        .subscribe({
          next: (clienteAtualizado) => {
            this.dados = this.dados.map((cliente) =>
              cliente.id === id ? clienteAtualizado : cliente
            );
            this.resetForm();
            this.snackBar.open("Cliente atualizado com sucesso!", "Fechar", {
              duration: 3000,
              horizontalPosition: "center",
              verticalPosition: "top",
              panelClass: ["success-snackbar"],
            });
          },
          error: (err) => {
            console.error("Failed to update cliente:", err);
            const errorMessage =
              err.error?.message ||
              "Não é possível atualizar. Verifique se o email ou telefone já estão cadastrados.";
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

  private resetForm() {
    this.form.reset();
    this.editing = false;
  }

  protected formatarTelefone(event: Event) {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, "");

    if (value.length <= 2) {
      value = value;
    } else if (value.length <= 6) {
      value = `(${value.slice(0, 2)}) ${value.slice(2)}`;
    } else if (value.length <= 10) {
      value = `(${value.slice(0, 2)}) ${value.slice(2, 6)}-${value.slice(6)}`;
    } else {
      value = `(${value.slice(0, 2)}) ${value.slice(2, 7)}-${value.slice(
        7,
        11
      )}`;
    }

    this.form.patchValue({ telefone: value }, { emitEvent: false });
  }
}
