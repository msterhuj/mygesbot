# 🤖 MyGesBot

## ❓Description

Bot discord pour faire interface entre l'api myges et discord

## 📰 Features

- [x] Lister les cours *avec index pour chercher les prochaine semaine de cours*
- [ ] Commands pour voir les cours du lendemain (wip)
- [ ] Commands pour la prochaine semaine de cours (wip)
- [ ] Genere un ics pour les cours
- [ ] Auto sync des fichiers de cours
- [ ] System de rappel pour devoir/interrogation

> Je suis open a toute suggestion

## 🔨 Build

### 🔩 Bare metal

```bash
gradle clean build jar
```

### 🐳 Docker

```bash
docker build -t mygesbot .
```

## 🏃 Run

### 🔩 Bare metal

> Set environment variables before running

```bash
export TOKEN=discord_token
export GES_USER=your_ges_user
export GES_PASS=your_ges_pass

java -jar mygesbot-0.1.0-standalone.jar
```

### 🐳 Docker

```bash
docker run -e TOKEN=your_token -e GES_USER=your_ges_token -e GES_PASS=your_ges_user mygesbot
```

## 💻 Commands

- `/agenda` - list tout les cours de la semaine actuel.
- `/agenda offset=X` - list tout les cours de dans X semaine.

## 📝 License

[GNU General Public License v3.0](LICENSE.md)

# 😄 Notes

Oui c'est du java ❤️