import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const token = localStorage.getItem('token');

  if(token){
    req = req.clone({
      setHeaders:{
        Authorization: `Bearer ${token}`
      }
    });
  } else {
    console.warn('No token found in localStorage. Request will be sent without Authorization header.');
  }
  return next(req);
};
