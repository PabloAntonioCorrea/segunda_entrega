import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home-component';
import { DashboardComponent } from './dashboard-component/dashboard-component';
import { ClienteComponent } from './cliente-component/cliente-component';
import { GarcomComponent } from './garcom-component/garcom-component';
import { PratoComponent } from './prato-component/prato-component';
import { PedidoComponent } from './pedido-component/pedido-component';
import { homeGuard } from '../../core/security/home-guard';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivateChild: [homeGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'clientes', component: ClienteComponent },
      { path: 'garcons', component: GarcomComponent },
      { path: 'pratos', component: PratoComponent },
      { path: 'pedidos', component: PedidoComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule {}

