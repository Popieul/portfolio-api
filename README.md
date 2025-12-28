# Portfolio Backend - CV PDF Generator

Backend Spring Boot pour la génération de CV PDF professionnels avec OpenHTMLtoPDF.

##  Démarrage Rapide

### Prérequis
- Java 17+
- Maven 3.8+

### Installation

```bash
# Télécharger les dépendances
mvn clean install

# Démarrer le serveur
mvn spring-boot:run
```

Le serveur démarre sur **http://localhost:8080**

### Test de l'API

```bash
# Health check
curl http://localhost:8080/api/cv/health
```

## 📡 API Endpoints

### POST /api/cv/generate

Génère un CV PDF à partir des données du profil et des expériences.

**Request Body:**
```json
{
  "profile": {
    "firstName": "Clément",
    "lastName": "Popieul",
    "title": "Développeur Full-Stack",
    "photoUrl": "data:image/jpeg;base64,...",
    "email": "clement.popieul@gmail.com",
    "phone": "06 12 34 56 78",
    ...
  },
  "experiences": [
    {
      "title": "Développeur Full-Stack",
      "company": "Entreprise",
      "startDate": "2023-01",
      "endDate": "Présent",
      "description": "...",
      "technologies": ["Angular", "Spring Boot"]
    }
  ]
}
```

**Response:**
- Content-Type: `application/pdf`
- Content-Disposition: `attachment; filename="CV_Prenom_Nom_2025-12-11.pdf"`
- Body: PDF binary

##  Architecture

```
src/main/java/com/portfolio/
├── PortfolioBackendApplication.java  # Main class
├── config/
│   └── CorsConfig.java                # CORS configuration
├── controller/
│   └── CvController.java              # REST endpoints
├── service/
│   └── PdfGeneratorService.java       # PDF generation logic
└── model/
    ├── CvProfile.java                 # Profile data model
    ├── Experience.java                # Experience data model
    └── CvRequest.java                 # Request DTO

src/main/resources/
├── application.properties             # App configuration
└── templates/
    └── cv-template.html               # CV HTML template (Thymeleaf)
```

##  Template CV

Le template HTML utilise:
- **Thymeleaf** pour le rendu dynamique
- **CSS moderne** avec gradients, shadows, border-radius
- **Layout 2 colonnes** : sidebar (70mm) + contenu principal
- **Photo professionnelle** avec bordure arrondie et effets
- **Responsive print** pour un rendu PDF optimal

## 🔧 Configuration

### application.properties

```properties
server.port=8080
spring.thymeleaf.cache=false
```

### CORS

Configuré pour autoriser les requêtes depuis:
- `http://localhost:4200` (Angular dev)

Pour modifier, éditer `CorsConfig.java`.

## 📦 Dépendances Principales

- **Spring Boot 3.2.0** - Framework
- **Spring Web** - REST API
- **Spring Thymeleaf** - Template engine
- **OpenHTMLtoPDF 1.0.10** - HTML to PDF conversion
- **Lombok** - Reduce boilerplate

##  Troubleshooting

### Le PDF ne se génère pas
- Vérifier que le backend est démarré (port 8080)
- Vérifier les logs dans la console
- Tester le endpoint `/api/cv/health`

### Erreur CORS
- Vérifier que le frontend tourne sur `http://localhost:4200`
- Modifier `CorsConfig.java` si nécessaire

### Photo ne s'affiche pas
- Vérifier que `photoUrl` est en base64 valide
- Format supporté: `data:image/jpeg;base64,...`

## 📝 Développement

### Modifier le template CV

Éditer `src/main/resources/templates/cv-template.html`

Le CSS est inline dans le template pour faciliter la conversion PDF.

### Ajouter des champs

1. Ajouter le champ dans `CvProfile.java`
2. Mettre à jour le template HTML
3. Redémarrer le serveur

##  Déploiement

### Build JAR

```bash
mvn clean package
java -jar target/portfolio-backend-1.0.0.jar
```

### Docker (optionnel)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/portfolio-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 📄 License

MIT


## PDF / OpenHTMLToPDF [28/12/2025]
- Génération OK
- CSS moderne partiellement ignoré (flex, object-fit)
- Warnings connus et acceptés pour V1
- ⚠️ Règle : ne PAS ajouter de nouvelles propriétés CSS non supportées
