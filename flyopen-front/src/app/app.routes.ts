import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { Constituent } from './features/constituent/constituent';
import { Imports } from './features/imports/imports';
import { Configuration } from './features/configuration/configuration';

export const routes: Routes = [
  {
    path: '',
    component: Login
  },
  {
    path: 'main-layout',
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'inicio', pathMatch: 'full' },
      {
        path: 'constituent',
        component: Constituent
      },
      {
        path: 'imports',
        component: Imports
      },
      {
        path: 'configuration',
        component: Configuration
      },
    ],
  },



  {
    path: '**',
    redirectTo: ''
  }
];
