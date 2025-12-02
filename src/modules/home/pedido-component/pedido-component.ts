import { Component, OnInit } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";
import { MatError, MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatSelect, MatOption } from "@angular/material/select";
import { FormsModule } from "@angular/forms";
import {
  FormBuilder,
  FormGroup,
  FormArray,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import {
  Pedido,
  PedidoRequest,
  StatusPedido,
} from "../../../core/models/pedido";
import { PedidoService } from "../../../core/services/pedido-service";
import { ClienteService } from "../../../core/services/cliente-service";
import { GarcomService } from "../../../core/services/garcom-service";
import { PratoService } from "../../../core/services/prato-service";
import { Cliente } from "../../../core/models/cliente";
import { Garcom } from "../../../core/models/garcom";
import { Prato } from "../../../core/models/prato";

@Component({
  selector: "app-pedido-component",
  imports: [
    MatIcon,
    MatButton,
    MatFormField,
    MatInput,
    MatSelect,
    MatOption,
    ReactiveFormsModule,
    FormsModule,
    MatLabel,
    MatError,
  ],
  templateUrl: "./pedido-component.html",
  styleUrl: "./pedido-component.css",
})
export class PedidoComponent implements OnInit {
  form: FormGroup = new FormGroup({});
  dados: Pedido[] = [];
  clientes: Cliente[] = [];
  garcons: Garcom[] = [];
  pratos: Prato[] = [];
  statusFiltro: StatusPedido | null = null;
  statusOptions = Object.values(StatusPedido);

  constructor(
    private pedidoService: PedidoService,
    private clienteService: ClienteService,
    private garcomService: GarcomService,
    private pratoService: PratoService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      clienteId: [null, [Validators.required]],
      garcomId: [null, [Validators.required]],
      itens: this.fb.array([this.criarItemFormGroup()]),
    });
  }

  ngOnInit(): void {
    this.carregarDados();
    this.carregarPedidos();
  }

  get itensFormArray(): FormArray {
    return this.form.get("itens") as FormArray;
  }

  criarItemFormGroup(): FormGroup {
    return this.fb.group({
      pratoId: [null, [Validators.required]],
      quantidade: [null, [Validators.required, Validators.min(1)]],
    });
  }

  adicionarItem(): void {
    this.itensFormArray.push(this.criarItemFormGroup());
  }

  removerItem(index: number): void {
    if (this.itensFormArray.length > 1) {
      this.itensFormArray.removeAt(index);
    }
  }

  carregarDados(): void {
    this.clienteService.getClientes().subscribe({
      next: (data) => {
        this.clientes = data;
      },
    });

    this.garcomService.getGarcons().subscribe({
      next: (data) => {
        this.garcons = data;
      },
    });

    this.pratoService.getPratos().subscribe({
      next: (data) => {
        this.pratos = data;
      },
    });
  }

  carregarPedidos(): void {
    if (this.statusFiltro) {
      this.pedidoService.getPedidosPorStatus(this.statusFiltro).subscribe({
        next: (pedidos) => {
          this.dados = pedidos;
        },
        error: (err) => {
          console.error("Failed to fetch pedidos:", err);
        },
      });
    } else {
      this.pedidoService.getPedidos().subscribe({
        next: (pedidos) => {
          this.dados = pedidos;
        },
        error: (err) => {
          console.error("Failed to fetch pedidos:", err);
        },
      });
    }
  }

  filtrarPorStatus(): void {
    this.carregarPedidos();
  }

  limparFiltro(): void {
    this.statusFiltro = null;
    this.carregarPedidos();
  }

  protected adicionarPedido() {
    if (this.form.valid) {
      const pedidoData: PedidoRequest = this.form.value;

      this.pedidoService.create(pedidoData).subscribe({
        next: (pedido) => {
          this.dados = [...this.dados, pedido];
          this.resetForm();
        },
        error: (err) => {
          console.error("Failed to create pedido:", err);
        },
      });
    }
  }

  atualizarStatus(id: number, status: StatusPedido): void {
    this.pedidoService.updateStatus(id, status).subscribe({
      next: () => {
        this.carregarPedidos();
      },
      error: (err) => {
        console.error("Failed to update status:", err);
      },
    });
  }

  cancelarPedido(id: number): void {
    if (confirm("Tem certeza que deseja cancelar este pedido?")) {
      this.pedidoService.cancelar(id).subscribe({
        next: () => {
          this.carregarPedidos();
        },
        error: (err) => {
          console.error("Failed to cancel pedido:", err);
        },
      });
    }
  }

  formatarData(data: string | undefined): string {
    if (!data) return "";
    return new Date(data).toLocaleString("pt-BR");
  }

  formatarPreco(preco: number | undefined): string {
    if (!preco) return "R$ 0,00";
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(preco);
  }

  getStatusLabel(status: StatusPedido): string {
    const labels: { [key: string]: string } = {
      EM_ABERTO: "Em Aberto",
      EM_PREPARACAO: "Em Preparação",
      PRONTO: "Pronto",
      ENTREGUE: "Entregue",
      CANCELADO: "Cancelado",
    };
    return labels[status] || status;
  }

  getStatusBadgeClass(status: StatusPedido): string {
    const classes: { [key: string]: string } = {
      EM_ABERTO: "badge status-em-aberto",
      EM_PREPARACAO: "badge status-em-preparacao",
      PRONTO: "badge status-pronto",
      ENTREGUE: "badge status-entregue",
      CANCELADO: "badge status-cancelado",
    };
    return classes[status] || "badge";
  }

  protected readonly StatusPedido = StatusPedido;

  private resetForm() {
    this.form.reset();
    this.itensFormArray.clear();
    this.itensFormArray.push(this.criarItemFormGroup());
  }
}
