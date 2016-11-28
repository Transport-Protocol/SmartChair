
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ChairComponent} from "./chair/chair.component";
import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";

const routes : Routes = [
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'chair/:uuid', component: ChairComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {  }