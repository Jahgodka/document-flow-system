import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DocumentService, Document } from './services/document'; 

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './app.html', 
  styleUrl: './app.scss'
})
export class App implements OnInit {
  documents: Document[] = [];

  newDocument: Document = {
    title: '',
    content: '',
    status: ''
  };

  constructor(private documentService: DocumentService,
              private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadDocuments();
  }

  loadDocuments(): void {
    this.documentService.getDocuments().subscribe({
      next: (data) => {
        this.documents = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Błąd pobierania dokumentów:', err);
      }
    });
  }

  onSubmit(): void {
    if (!this.newDocument.title || !this.newDocument.content) {
      alert('Tytuł i treść są wymagane!');
      return;
    }

    this.documentService.createDocument(this.newDocument).subscribe({
      next: (savedDoc) => {
        this.documents.push(savedDoc);
        this.newDocument = { title: '', content: '', status: '' };
        // Ręcznie wymuszamy odświeżenie widoku HTML.
        // Po asynchronicznym strzale do zewnętrznego API, Angular "przesypia" zmiany
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Błąd tworzenia dokumentu:', err);
      }
    });
  }

  onDelete(id: number | undefined): void {
    if (!id) return;

    this.documentService.deleteDocument(id).subscribe({
      next: () => {
        this.documents = this.documents.filter(doc => doc.id !== id);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Błąd usuwania dokumentu:', err);
      }
    });
  }
}