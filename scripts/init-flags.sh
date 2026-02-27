#!/bin/bash


UNLEASH_URL="http://localhost:4242/api/admin/projects/default/features"
ADMIN_TOKEN="*:*.default-token"

echo "Initializing Unleash Feature Flags..."


curl -s -X POST "$UNLEASH_URL" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "premium-pricing",
    "description": "Enable premium pricing discounts",
    "type": "release"
  }'


curl -s -X POST "$UNLEASH_URL/premium-pricing/environments/development/on" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

echo -e "\nCreated and enabled: premium-pricing"


curl -s -X POST "$UNLEASH_URL" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "order-notifications",
    "description": "Log order confirmation notifications",
    "type": "release"
  }'


curl -s -X POST "$UNLEASH_URL/order-notifications/environments/development/on" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

echo -e "\nCreated and enabled: order-notifications"


curl -s -X POST "$UNLEASH_URL" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "bulk-order-discount",
    "description": "Apply 15% discount when order quantity exceeds 5",
    "type": "release"
  }'


curl -s -X POST "$UNLEASH_URL/bulk-order-discount/environments/development/on" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

echo -e "\nCreated and enabled: bulk-order-discount"

echo -e "\nFeature flags initialization completed!"
