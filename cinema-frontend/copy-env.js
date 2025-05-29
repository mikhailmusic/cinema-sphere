import fs from 'fs';
import path from 'path';
import dotenv from 'dotenv';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const rootEnvPath = path.resolve(__dirname, '../.env');
const frontendEnvPath = path.resolve(__dirname, '.env');

const rootEnv = dotenv.parse(fs.readFileSync(rootEnvPath));

const filteredEnv = Object.entries(rootEnv)
  .filter(([key]) => key.startsWith('VITE_'))
  .map(([key, value]) => `${key}=${value}`)
  .join('\n');

fs.writeFileSync(frontendEnvPath, filteredEnv);
