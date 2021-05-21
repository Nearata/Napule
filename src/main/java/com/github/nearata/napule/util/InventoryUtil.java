package com.github.nearata.napule.util;

public final class InventoryUtil
{
    public static final String getModuleKey(final String text)
    {
        if (text == null || text.isEmpty())
        {
            return text;
        }

        StringBuilder sb = new StringBuilder();

        for (char ch : text.toCharArray())
        {
            if (Character.isSpaceChar(ch))
            {
                sb.append("_");
            }
            else if (Character.isUpperCase(ch))
            {
                ch = Character.toLowerCase(ch);
                sb.append(ch);
            }
            else
            {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
