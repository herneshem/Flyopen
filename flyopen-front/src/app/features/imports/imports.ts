import { Component, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ConstituentApi } from '../../core/services/constituent-api';

@Component({
  selector: 'app-imports',
  templateUrl: './imports.html',
  styleUrl: './imports.scss',
})
export class Imports {
  private api = inject(ConstituentApi);

  selectedFile = signal<File | null>(null);
  isLoading = signal(false);
  message = signal<string | null>(null);
  error = signal<string | null>(null);

  selectFile(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile.set(input.files?.[0] ?? null);
    this.message.set(null);
    this.error.set(null);
  }

  importFile(): void {
    const file = this.selectedFile();
    if (!file) return;

    this.isLoading.set(true);
    this.message.set(null);
    this.error.set(null);

    this.api.importFile(file).subscribe({
      next: (result) => {
        this.message.set(`${result.message} Filas procesadas: ${result.processedRows}`);
        this.isLoading.set(false);
      },
      error: (httpError: HttpErrorResponse) => {
        this.error.set(httpError.error?.error ?? 'No se pudo importar el archivo.');
        this.isLoading.set(false);
      },
    });
  }

  exportExcel(): void {
    this.api.exportFile().subscribe((blob) => {
      const downloadUrl = URL.createObjectURL(blob);
      const downloadLink = document.createElement('a');
      downloadLink.href = downloadUrl;
      downloadLink.download = 'components.xlsx';
      downloadLink.click();
      URL.revokeObjectURL(downloadUrl);
    });
  }
}