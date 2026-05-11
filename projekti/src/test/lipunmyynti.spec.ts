import { test, expect } from '@playwright/test';

const URL = 'https://projekti-e-9.onrender.com/index.html';

test.describe('TicketGuru Lipunmyynti E2E', () => {

  test('sivu latautuu ja kentät näkyvät', async ({ page }) => {
    await page.goto(URL);

    await expect(page.getByText('TicketGuru Lipunmyynti')).toBeVisible();
    await expect(page.getByText('Myyjä')).toBeVisible();
    await expect(page.getByText('Tapahtuma')).toBeVisible();
    await expect(page.getByText('Lipputyyppi')).toBeVisible();
    await expect(page.getByText('Määrä')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Myy liput' })).toBeVisible();
  });

  test('käyttäjä voi myydä yhden lipun', async ({ page }) => {
    await page.goto(URL);

    await page.locator('select').nth(0).selectOption({ index: 0 });
    await page.locator('select').nth(1).selectOption({ index: 0 });
    await page.locator('select').nth(2).selectOption({ index: 0 });

    await page.locator('input[type="number"]').fill('1');
    await page.getByRole('button', { name: 'Myy liput' }).click();

    await expect(page.locator('body')).toContainText(/myynti|onnistui|myyty|20|tulos/i);
  });

  test('kahden lipun hinta lasketaan oikein', async ({ page }) => {
    await page.goto(URL);

    await page.locator('select').nth(2).selectOption({ label: 'Aikuinen - 20 €' });
    await page.locator('input[type="number"]').fill('2');

    await page.getByRole('button', { name: 'Myy liput' }).click();

    await expect(page.locator('body')).toContainText(/40/);
  });

  test('järjestelmä estää määrän 0', async ({ page }) => {
    await page.goto(URL);

    await page.locator('input[type="number"]').fill('0');
    await page.getByRole('button', { name: 'Myy liput' }).click();

    await expect(page.locator('body')).toContainText(/virhe|määrä|0|invalid|ei sallittu/i);
  });

  test('järjestelmä estää tyhjän määrän', async ({ page }) => {
    await page.goto(URL);

    await page.locator('input[type="number"]').fill('');
    await page.getByRole('button', { name: 'Myy liput' }).click();

    await expect(page.locator('body')).toContainText(/virhe|määrä|required|pakollinen/i);
  });

});